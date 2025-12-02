import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FiService } from '../../services/fi.service';
import { NotificationService } from '../../services/notification.service';
import { FiRequestDTO } from '../../models/fi-data.model';

@Component({
  selector: 'app-fi-request',
  templateUrl: './fi-request.component.html',
  styleUrls: ['./fi-request.component.css']
})
export class FiRequestComponent implements OnInit {
  fiRequestForm!: FormGroup;
  consentRequestId!: string;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private fiService: FiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.consentRequestId = this.route.snapshot.paramMap.get('consentId')!;
    this.initializeForm();
  }

  initializeForm() {
    const today = new Date();
    const oneMonthAgo = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());

    this.fiRequestForm = this.fb.group({
      from: [this.formatDateTime(oneMonthAgo), Validators.required],
      to: [this.formatDateTime(today), Validators.required]
    });
  }

  formatDateTime(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}T00:00:00`;
  }

  onSubmit() {
    if (this.fiRequestForm.invalid) {
      this.notificationService.showError('Please fill all required fields');
      return;
    }

    this.loading = true;
    const formValue = this.fiRequestForm.value;

    const request: FiRequestDTO = {
      from: formValue.from,
      to: formValue.to
    };

    this.fiService.createFiRequest(this.consentRequestId, request).subscribe({
      next: (response) => {
        this.loading = false;
        this.notificationService.showSuccess('FI request created successfully!');
        this.router.navigate(['/fi/data', this.consentRequestId]);
      },
      error: (error) => {
        this.loading = false;
        console.error('Error creating FI request:', error);
      }
    });
  }

  onCancel() {
    this.router.navigate(['/consent/details', this.consentRequestId]);
  }
}