import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ConsentCreateComponent } from './components/consent-create/consent-create.component';
import { ConsentListComponent } from './components/consent-list/consent-list.component';
import { ConsentDetailsComponent } from './components/consent-details/consent-details.component';
import { FiRequestComponent } from './components/fi-request/fi-request.component';
import { FiDataViewComponent } from './components/fi-data-view/fi-data-view.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'consent/create', component: ConsentCreateComponent },
  { path: 'consent/list', component: ConsentListComponent },
  { path: 'consent/details/:id', component: ConsentDetailsComponent },
  { path: 'fi/request/:id', component: FiRequestComponent },
  { path: 'fi/data/:id', component: FiDataViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
