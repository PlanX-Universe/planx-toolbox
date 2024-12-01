import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { environment } from '../environments/environment';
import { LoginGuardService } from './guards/login-guard.service';

export const FALLBACK_PAGE = 'login';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home').then(mod => mod.HomeRouterModule),
    canActivate: [LoginGuardService]
  },
  {
    path: 'licences',
    loadChildren: () => import('./licences').then(mod => mod.LicencesRouterModule),
    canActivate: [LoginGuardService]
  },
  {
    path: 'admin',
    loadChildren: () => import('@planx-planning/dev-ui')
      .then(mod => mod.DevUiRoutingModule),
    canActivate: [LoginGuardService]
  },
  {
    path: 'login',
    loadChildren: () => import('@planx-planning/login')
      .then(mod => mod.LoginRoutingModule)
  },
  { path: '', redirectTo: environment.START_PAGE, pathMatch: 'full' },
  { path: '**', redirectTo: FALLBACK_PAGE }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
