import {InvoiceDetail} from './invoice-detail';
import {User} from './user';

export interface Invoice {
  id?: number;
  code?: string;
  time?: Date;
  status?: boolean;
  user?: User;
  invoiceDetail?: InvoiceDetail[];
}
