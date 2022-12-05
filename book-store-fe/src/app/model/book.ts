import {Author} from './author';
import {PublishingCompany} from './publishing-company';
import {Translator} from './translator';

export interface Book {
  id?: number;
  title?: string;
  numberOfPages?: number;
  size?: string;
  summary?: string;
  releaseDate?: Date;
  imageUrl?: string;
  price?: number;
  quantity?: number;
  author?: Author;
  publishingCompany?: PublishingCompany;
  translator?: Translator;
}
