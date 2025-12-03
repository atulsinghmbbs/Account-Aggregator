export interface ConsentRequestDTO {
  customer_details: CustomerDetails;
  customer_id?: string;
  template_id: string;
  consent_details: ConsentDetails;
  customer_notification_mode?: string;
  notify_customer?: boolean;
}

export interface CustomerDetails {
  customer_name: string;
  customer_email: string;
  customer_ref_id?: string;
  customer_mobile: string;
  customer_identifier: string;
}

export interface ConsentDetails {
  consent_start_date: string;
  consent_expiry_date: string;
  fi_start_date: string;
  fi_end_date: string;
  meta?: ConsentMeta;
}

export interface ConsentMeta {
  fip_ids?: string[];
  email_id?: string;
  dob?: string;
  show_consent_info?: boolean;
}

export interface ConsentResponseDTO {
  consent_request_id: string;
  customer_details: CustomerDetails;
  status: string;
  consent_details: ConsentDetails;
  gateway_token_id: string;
  created_at: string;
}

export enum ConsentStatus {
  PENDING = 'PENDING',
  REQUESTED = 'REQUESTED',
  APPROVED = 'APPROVED',
  ACTIVE = 'ACTIVE',
  PAUSED = 'PAUSED',
  REJECTED = 'REJECTED',
  REVOKED = 'REVOKED',
  EXPIRED = 'EXPIRED'
}