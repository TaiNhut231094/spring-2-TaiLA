import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CartComponent} from './cart/cart.component';
import {AuthGuardService} from '../service/auth-guard.service';


const routes: Routes = [
  {
    path: '', component: CartComponent, canActivate: [AuthGuardService], data: {role: ['ROLE_ADMIN', 'ROLE_USER']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvoiceRoutingModule { }
