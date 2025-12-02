var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { Component } from '@angular/core';
export let ConsentComponent = class ConsentComponent {
    http;
    customerId = '';
    consentId = '';
    response;
    constructor(http) {
        this.http = http;
    }
    createConsent() {
        this.http.post('/api/aa/consent/create', { customerId: this.customerId })
            .subscribe((res) => { this.consentId = res.consentRequestId; this.response = res; });
    }
    checkStatus() {
        this.http.get('/api/aa/consent/status/' + this.consentId)
            .subscribe(r => this.response = r);
    }
};
ConsentComponent = __decorate([
    Component({
        selector: 'consent-flow',
        template: `
    <div>
      <h3>Create Consent</h3>
      <input [(ngModel)]="customerId" placeholder="Customer ID"/>
      <button (click)="createConsent()">Create</button>

      <div *ngIf="consentId">
        <p>Consent ID: {{consentId}}</p>
        <button (click)="checkStatus()">Check Status</button>
      </div>

      <pre>{{response | json}}</pre>
    </div>
  `
    })
], ConsentComponent);
//# sourceMappingURL=consent.component.js.map