import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ConsentRequestDTO, ConsentResponseDTO } from '../models/consent.model';

@Injectable({
  providedIn: 'root'
})
export class ConsentService {
  private apiUrl = `${environment.apiUrl}/consent`;

  constructor(private http: HttpClient) {}

  createConsentRequest(request: ConsentRequestDTO): Observable<ConsentResponseDTO> {
    return this.http.post<ConsentResponseDTO>(`${this.apiUrl}/request`, request);
  }

  getConsentDetails(consentRequestId: string): Observable<ConsentResponseDTO> {
    return this.http.get<ConsentResponseDTO>(`${this.apiUrl}/request/${consentRequestId}/details`);
  }

  getAllConsents(): Observable<ConsentResponseDTO[]> {
    return this.http.get<ConsentResponseDTO[]>(`${this.apiUrl}/requests`);
  }

  getConsentsByCustomer(customerIdentifier: string): Observable<ConsentResponseDTO[]> {
    return this.http.get<ConsentResponseDTO[]>(`${this.apiUrl}/customer/${customerIdentifier}`);
  }
}