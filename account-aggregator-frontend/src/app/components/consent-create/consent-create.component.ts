import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConsentService } from '../../services/consent.service';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-consent-create',
  templateUrl: './consent-create.component.html',
  styleUrls: ['./consent-create.component.css']
})
export class ConsentCreateComponent implements OnInit {

  consentForm!: FormGroup;
  loading = false;
  templateId = 'CTMP250926152142855LYQ59RGKJTAGZ';

  constructor(
    private fb: FormBuilder,
    private consentService: ConsentService,
    private notificationService: NotificationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }


  private formatDateForBackend(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  initializeForm() {
    const today = new Date();
    const defaultStart = new Date();
    const defaultExpiry = new Date();
    defaultExpiry.setDate(today.getDate() + 25); // Max allowed ~1 month (safe)

    const fiStart = new Date();
    fiStart.setFullYear(today.getFullYear() - 1);

    this.consentForm = this.fb.group({
      customerName: ['', [Validators.required, Validators.minLength(2)]],
      customerEmail: ['', [Validators.required, Validators.email]],
      customerMobile: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      customerRefId: [''],
      
    
      consentStartDate: [this.toInputDateTime(defaultStart), Validators.required],
      consentExpiryDate: [this.toInputDateTime(defaultExpiry), Validators.required],
      fiStartDate: [this.toInputDateTime(fiStart), Validators.required],
      fiEndDate: [this.toInputDateTime(today), Validators.required],

      fipIds: [['FINSHARE_FIP']],
      showConsentInfo: [true],
      notifyCustomer: [true],
      customerNotificationMode: ['SMS']
    });
  }


  private toInputDateTime(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  onSubmit() {
    if (this.consentForm.invalid) {
      this.notificationService.showError('Please fill all required fields correctly');
      return;
    }

    this.loading = true;
    const f = this.consentForm.value;

    const payload = {
      customer_details: {
        customer_name: f.customerName,
        customer_email: f.customerEmail,
        customer_mobile: f.customerMobile,
        customer_identifier: f.customerMobile,
        customer_ref_id: f.customerRefId || undefined
      },
      customer_id: '',
      template_id: this.templateId,
      consent_details: {
        consent_start_date: this.formatDateForBackend(new Date(f.consentStartDate)),
        consent_expiry_date: this.formatDateForBackend(new Date(f.consentExpiryDate)),
        fi_start_date: this.formatDateForBackend(new Date(f.fiStartDate)),
        fi_end_date: this.formatDateForBackend(new Date(f.fiEndDate)),
        meta: {
          fip_ids: f.fipIds,
          show_consent_info: f.showConsentInfo
        }
      },
      notify_customer: f.notifyCustomer,
      customer_notification_mode: f.customerNotificationMode
    };

    console.log('Sending Payload →', JSON.stringify(payload, null, 2));

    this.consentService.createConsentRequest(payload).subscribe({
      next: (res: any) => {
        this.loading = false;
        this.notificationService.showSuccess('Consent created successfully!');
        this.router.navigate(['/consent/details', res.consent_request_id || res.id]);
      },
      error: (err) => {
        this.loading = false;
        this.notificationService.showError(err.error?.errorMsg || 'Failed to create consent');
      }
    });
  }

  onCancel() {
    this.router.navigate(['/dashboard']);
  }
}