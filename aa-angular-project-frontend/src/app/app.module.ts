import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module'; 

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ConsentCreateComponent } from './components/consent-create/consent-create.component';
import { ConsentListComponent } from './components/consent-list/consent-list.component';
import { ConsentDetailsComponent } from './components/consent-details/consent-details.component';
import { FiRequestComponent } from './components/fi-request/fi-request.component';
import { FiDataViewComponent } from './components/fi-data-view/fi-data-view.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ConsentCreateComponent,
    ConsentListComponent,
    ConsentDetailsComponent,
    FiRequestComponent,
    FiDataViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,        
    FormsModule,
    ReactiveFormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
