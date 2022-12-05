import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { ListComponent } from './list/list.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
    declarations: [ListComponent],
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
