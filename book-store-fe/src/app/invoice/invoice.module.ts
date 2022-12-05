import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InvoiceRoutingModule } from './invoice-routing.module';
import { CartComponent } from './cart/cart.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [CartComponent],
    imports: [
        CommonModule,
        InvoiceRoutingModule,
        FormsModule
    ]
})
export class InvoiceModule { }
