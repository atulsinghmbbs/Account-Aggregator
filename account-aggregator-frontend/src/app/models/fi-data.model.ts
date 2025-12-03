export interface FiRequestDTO {
  from: string;
  to: string;
}

export interface FiResponseDTO {
  fi_request_id: string;
  fi_start_date: string;
  fi_end_date: string;
  status: string;
  consent_request_id: string;
  fi?: FiDataItem[];
}

export interface FiDataItem {
  fi_data: any;
  fip_id: string;
  fi_type: string;
  account_number: string;
  account_id: string;
  fi_data_id: string;
}

export enum FiRequestStatus {
  REQUESTED = 'REQUESTED',
  DATA_AVAILABLE = 'DATA_AVAILABLE',
  PARTIAL = 'PARTIAL',
  FAILED = 'FAILED',
  EXPIRED = 'EXPIRED'
}