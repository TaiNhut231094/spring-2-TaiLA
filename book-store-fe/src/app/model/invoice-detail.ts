import {Book} from './book';
import {Invoice} from './invoice';

export interface InvoiceDetail {
  quantity?: number;
  book?: Book;
  invoice?: Invoice;
}
