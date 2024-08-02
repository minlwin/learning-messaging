import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TownshipComponent } from './pages/township/township.component';
import { InvoicesComponent } from './pages/invoices/invoices.component';
import { ErrorsComponent } from './pages/errors/errors.component';

export const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'township', component: TownshipComponent},
  {path: 'invoices', component: InvoicesComponent},
  {path: 'errors', component: ErrorsComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];
