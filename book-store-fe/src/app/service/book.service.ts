import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../model/book';
import {Category} from '../model/category';


const API_URL = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) {
  }

  getAllBook(index: number, category: string, title: string, author: string): Observable<any> {
    return this.http.get<any>(API_URL + 'book/search?page=' + index + '&category=' +
      category + '&title=' + title + '&author=' + author);
  }

  getAllCategory(): Observable<Category[]> {
    return this.http.get<Category[]>(API_URL + 'book/category');
  }

  getCart() {
    const cartJson = sessionStorage.getItem('cart');
    if (cartJson) {
      return JSON.parse(cartJson);
    } else {
      return [];
    }
  }
}
