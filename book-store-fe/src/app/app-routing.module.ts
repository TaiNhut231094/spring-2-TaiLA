import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./book/book.module').then(module => module.BookModule),
  },
  {
    path: 'security',
    loadChildren: () => import('./security/security.module').then(module => module.SecurityModule),
  },
  {
    path: 'cart',
    loadChildren: () => import('./invoice/invoice.module').then(module => module.InvoiceModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
