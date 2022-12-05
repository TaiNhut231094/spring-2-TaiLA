import {Address} from './address';
import {Invoice} from './invoice';

export interface User {
  id?: number;
  name?: string;
  email?: string;
  phoneNumber?: string;
  gender?: boolean;
  avatar?: string;
  address?: Address;
  invoice?: Invoice;
}
