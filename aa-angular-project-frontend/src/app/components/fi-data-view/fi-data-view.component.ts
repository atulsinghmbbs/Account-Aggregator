import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FiService } from '../../services/fi.service';
import { FiResponseDTO } from '../../models/fi-data.model';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-fi-data-view',
  templateUrl: './fi-data-view.component.html',
  styleUrls: ['./fi-data-view.component.css']
})
export class FiDataViewComponent implements OnInit {
  consentRequestId!: string;
  fiData: FiResponseDTO | null = null;
  loading = false;
  selectedAccount: any = null;
  viewMode: 'summary' | 'detailed' = 'summary';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fiService: FiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.consentRequestId = this.route.snapshot.paramMap.get('consentId')!;
    this.loadFiData();
  }

  loadFiData() {
    this.loading = true;
    this.fiService.getFiDataByConsentId(this.consentRequestId).subscribe({
      next: (data) => {
        this.fiData = data;
        this.loading = false;
        
        if (!data.fi || data.fi.length === 0) {
          this.notificationService.showInfo('No financial data available yet. Please wait for FIP response.');
        }
      },
      error: (error) => {
        this.loading = false;
        console.error('Error loading FI data:', error);
      }
    });
  }

  refresh() {
    this.loadFiData();
    this.notificationService.showInfo('Refreshing FI data...');
  }

  viewAccountDetails(account: any) {
    this.selectedAccount = account;
    this.viewMode = 'detailed';
  }

  closeAccountDetails() {
    this.selectedAccount = null;
    this.viewMode = 'summary';
  }

  downloadData() {
    if (!this.fiData) return;
    
    const dataStr = JSON.stringify(this.fiData, null, 2);
    const dataBlob = new Blob([dataStr], { type: 'application/json' });
    const url = window.URL.createObjectURL(dataBlob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `fi-data-${this.consentRequestId}.json`;
    link.click();
    window.URL.revokeObjectURL(url);
    
    this.notificationService.showSuccess('FI data downloaded successfully');
  }

  goBack() {
    this.router.navigate(['/consent/details', this.consentRequestId]);
  }

  getStatusClass(status: string): string {
    const statusMap: { [key: string]: string } = {
      'REQUESTED': 'status-requested',
      'DATA_AVAILABLE': 'status-active',
      'PARTIAL': 'status-pending',
      'FAILED': 'status-rejected',
      'EXPIRED': 'status-expired'
    };
    return statusMap[status] || 'status-default';
  }
}