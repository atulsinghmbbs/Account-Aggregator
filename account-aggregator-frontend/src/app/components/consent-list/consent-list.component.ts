import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConsentService } from '../../services/consent.service';
import { ConsentResponseDTO } from '../../models/consent.model';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-consent-list',
  templateUrl: './consent-list.component.html',
  styleUrls: ['./consent-list.component.css']
})
export class ConsentListComponent implements OnInit {
  consents: ConsentResponseDTO[] = [];
  filteredConsents: ConsentResponseDTO[] = [];
  loading = false;
  searchTerm = '';
  filterStatus = 'ALL';

  constructor(
    private consentService: ConsentService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadConsents();
  }

  loadConsents() {
    this.loading = true;
    this.consentService.getAllConsents().subscribe({
      next: (data) => {
        this.consents = data;
        this.filteredConsents = data;
        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
        console.error('Error loading consents:', error);
      }
    });
  }

  onSearch() {
    this.applyFilters();
  }

  onFilterChange() {
    this.applyFilters();
  }

  applyFilters() {
    this.filteredConsents = this.consents.filter(consent => {
      const matchesSearch = !this.searchTerm || 
        consent.customer_details.customer_name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        consent.customer_details.customer_mobile.includes(this.searchTerm) ||
        consent.consent_request_id.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatus = this.filterStatus === 'ALL' || consent.status === this.filterStatus;

      return matchesSearch && matchesStatus;
    });
  }

  viewDetails(consentRequestId: string) {
    this.router.navigate(['/consent/details', consentRequestId]);
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

  refresh() {
    this.loadConsents();
    this.notificationService.showInfo('Refreshing consent list...');
  }
}