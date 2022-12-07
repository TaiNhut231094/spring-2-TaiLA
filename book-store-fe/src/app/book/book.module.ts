import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { ListComponent } from './list/list.component';
import {ReactiveFormsModule} from '@angular/forms';
import { DetailComponent } from './detail/detail.component';


@NgModule({
    declarations: [ListComponent, DetailComponent],
    exports: [
        ListComponent
    ],
    imports: [
        CommonModule,
        BookRoutingModule,
        ReactiveFormsModule
    ]
})
export class BookModule { }
