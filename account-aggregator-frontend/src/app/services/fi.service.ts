import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { FiRequestDTO, FiResponseDTO } from '../models/fi-data.model';

@Injectable({
  providedIn: 'root'
})
export class FiService {
  private apiUrl = `${environment.apiUrl}/fi`;

  constructor(private http: HttpClient) {}

  createFiRequest(consentRequestId: string, request: FiRequestDTO): Observable<FiResponseDTO> {
    return this.http.post<FiResponseDTO>(`${this.apiUrl}/request/${consentRequestId}`, request);
  }

  getFiRequestDetails(fiRequestId: string): Observable<FiResponseDTO> {
    return this.http.get<FiResponseDTO>(`${this.apiUrl}/request/${fiRequestId}/details`);
  }

  getFiDataByConsentId(consentRequestId: string): Observable<FiResponseDTO> {
    return this.http.get<FiResponseDTO>(`${this.apiUrl}/consent/${consentRequestId}/data`);
  }

  getFiRequestsByConsent(consentRequestId: string): Observable<FiResponseDTO[]> {
    return this.http.get<FiResponseDTO[]>(`${this.apiUrl}/consent/${consentRequestId}/requests`);
  }
}