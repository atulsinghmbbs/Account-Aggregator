import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConsentService } from '../../services/consent.service';
import { FiService } from '../../services/fi.service';
import { ConsentResponseDTO } from '../../models/consent.model';
import { FiResponseDTO } from '../../models/fi-data.model';
import { NotificationService } from '../../services/notification.service';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-consent-details',
  templateUrl: './consent-details.component.html',
  styleUrls: ['./consent-details.component.css']
})
export class ConsentDetailsComponent implements OnInit, OnDestroy {
  consentRequestId!: string;
  consent: ConsentResponseDTO | null = null;
  fiRequests: FiResponseDTO[] = [];
  loading = false;
  autoRefresh = false;
  refreshSubscription?: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private consentService: ConsentService,
    private fiService: FiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.consentRequestId = this.route.snapshot.paramMap.get('id')!;
    this.loadConsentDetails();
    this.loadFiRequests();
  }

  ngOnDestroy(): void {
    this.stopAutoRefresh();
  }

  loadConsentDetails() {
    this.loading = true;
    this.consentService.getConsentDetails(this.consentRequestId).subscribe({
      next: (data) => {
        this.consent = data;
        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
        console.error('Error loading consent details:', error);
      }
    });
  }

  loadFiRequests() {
    this.fiService.getFiRequestsByConsent(this.consentRequestId).subscribe({
      next: (data) => {
        this.fiRequests = data;
      },
      error: (error) => {
        console.error('Error loading FI requests:', error);
      }
    });
  }

  refresh() {
    this.loadConsentDetails();
    this.loadFiRequests();
    this.notificationService.showInfo('Refreshing consent details...');
  }

  toggleAutoRefresh() {
    this.autoRefresh = !this.autoRefresh;
    
    if (this.autoRefresh) {
      this.refreshSubscription = interval(10000).subscribe(() => {
        this.loadConsentDetails();
        this.loadFiRequests();
      });
      this.notificationService.showInfo('Auto-refresh enabled (every 10 seconds)');
    } else {
      this.stopAutoRefresh();
      this.notificationService.showInfo('Auto-refresh disabled');
    }
  }

  stopAutoRefresh() {
    if (this.refreshSubscription) {
      this.refreshSubscription.unsubscribe();
      this.autoRefresh = false;
    }
  }

  createFiRequest() {
    this.router.navigate(['/fi/request', this.consentRequestId]);
  }

  viewFiData() {
    this.router.navigate(['/fi/data', this.consentRequestId]);
  }

  viewFiRequestDetails(fiRequestId: string) {
    this.router.navigate(['/fi/details', fiRequestId]);
  }

  getStatusClass(status: string): string {
    const statusMap: { [key: string]: string } = {
      'PENDING': 'status-pending',
      'REQUESTED': 'status-requested',
      'ACTIVE': 'status-active',
      'APPROVED': 'status-active',
      'REJECTED': 'status-rejected',
      'EXPIRED': 'status-expired',
      'REVOKED': 'status-revoked'
    };
    return statusMap[status] || 'status-default';
  }

  canCreateFiRequest(): boolean {
    return this.consent?.status === 'ACTIVE' || this.consent?.status === 'APPROVED';
  }

  goBack() {
    this.router.navigate(['/consent/list']);
  }
}