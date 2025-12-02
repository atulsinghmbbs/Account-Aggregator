import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConsentService } from '../../services/consent.service';
import { NotificationService } from '../../services/notification.service';
import { ConsentRequestDTO } from '../../models/consent.model';

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
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    const today = new Date();
    const oneYearLater = new Date(today.getFullYear() + 1, today.getMonth(), today.getDate());
    const oneYearAgo = new Date(today.getFullYear() - 1, today.getMonth(), today.getDate());

    this.consentForm = this.fb.group({
      customerName: ['', [Validators.required, Validators.minLength(3)]],
      customerEmail: ['', [Validators.required, Validators.email]],
      customerMobile: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      customerRefId: [''],
      consentStartDate: [this.formatDate(today), Validators.required],
      consentExpiryDate: [this.formatDate(oneYearLater), Validators.required],
      fiStartDate: [this.formatDate(oneYearAgo), Validators.required],
      fiEndDate: [this.formatDate(today), Validators.required],
      fipIds: [['FINSHARE_BANK']],
      emailId: [''],
      dob: [''],
      showConsentInfo: [true],
      notifyCustomer: [true],
      customerNotificationMode: ['SMS']
    });
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}T00:00:00`;
  }

  onSubmit() {
    if (this.consentForm.invalid) {
      this.notificationService.showError('Please fill all required fields correctly');
      return;
    }

    this.loading = true;
    const formValue = this.consentForm.value;

    const request: ConsentRequestDTO = {
      customer_details: {
        customer_name: formValue.customerName,
        customer_email: formValue.customerEmail,
        customer_mobile: formValue.customerMobile,
        customer_identifier: formValue.customerMobile,
        customer_ref_id: formValue.customerRefId || undefined
      },
      customer_id: '',
      template_id: this.templateId,
      consent_details: {
        consent_start_date: formValue.consentStartDate,
        consent_expiry_date: formValue.consentExpiryDate,
        fi_start_date: formValue.fiStartDate,
        fi_end_date: formValue.fiEndDate,
        meta: {
          fip_ids: formValue.fipIds,
          email_id: formValue.emailId || undefined,
          dob: formValue.dob || undefined,
          show_consent_info: formValue.showConsentInfo
        }
      },
      notify_customer: formValue.notifyCustomer,
      customer_notification_mode: formValue.customerNotificationMode
    };

    this.consentService.createConsentRequest(request).subscribe({
      next: (response) => {
        this.loading = false;
        this.notificationService.showSuccess('Consent request created successfully!');
        this.router.navigate(['/consent/details', response.consent_request_id]);
      },
      error: (error) => {
        this.loading = false;
        console.error('Error creating consent:', error);
      }
    });
  }

  onCancel() {
    this.router.navigate(['/dashboard']);
  }
}