import { Component, OnInit } from '@angular/core';
import {BookService} from '../../service/book.service';
import {CartService} from '../../service/cart.service';
import {DataService} from '../../service/data.service';
import {ShareService} from '../../service/share.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {Book} from '../../model/book';
import {Category} from '../../model/category';
import Swal from "sweetalert2";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  username: string;
  currentUser: string;
  role: string;
  isLoggedIn = false;

  books: Book[] = [];
  categories: Category[] = [];

  book: Book;
  id: number;

  carts: any = this.cartService.getCart();

  constructor(private bookService: BookService,
              private cartService: CartService,
              private dataService: DataService,
              private shareService: ShareService,
              private tokenStorageService: TokenStorageService,
              private activatedRoute: ActivatedRoute,
              private title: Title) {
    this.title.setTitle('Chi tiết sách');
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.id = +paramMap.get('id');
      this.findById(this.id);
    });
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
  }

  ngOnInit(): void {
  }

  findById(id: number) {
    return this.bookService.findById(id).subscribe(book => {
      this.book = book;
      console.log(this.book);
    });
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser().username;
      this.role = this.tokenStorageService.getUser().role[0];
      this.username = this.tokenStorageService.getUser().username;
    }
    this.isLoggedIn = this.username != null;
  }

  onAddToCart(book: Book) {
    const index = this.carts.findIndex((item) => {
      return item.id === book.id;
    });
    if (index >= 0) {
      this.carts[index].quantity += 1;
    } else {
      const cartItem: any = {
        id: book.id,
        title: book.title,
        price: book.price,
        size: book.size,
        quantity: 1,
        imageUrl: book.imageUrl
      };
      this.carts.push(cartItem);
    }
    this.cartService.saveCart(this.carts);
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
    Swal.fire('Thông Báo !!', 'Đã thêm vào giỏ hàng', 'success').then();
  }
}
