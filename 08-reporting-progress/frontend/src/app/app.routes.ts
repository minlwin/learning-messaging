import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TownshipComponent } from './pages/township/township.component';

export const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'township', component: TownshipComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];
