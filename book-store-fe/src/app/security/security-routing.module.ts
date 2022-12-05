import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SignInComponent} from './sign-in/sign-in.component';
import {SignUpComponent} from './sign-up/sign-up.component';


const routes: Routes = [
  {
    path: 'sign-in', component: SignInComponent, data: {title: 'Sign-in'}
  },
  {
    path: 'sign-up', component: SignUpComponent, data: {title: 'Sign-up'}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SecurityRoutingModule { }
