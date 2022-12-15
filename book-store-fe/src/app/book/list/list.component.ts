import {Component, OnInit} from '@angular/core';
import {Book} from '../../model/book';
import {BookService} from '../../service/book.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {Category} from '../../model/category';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CartService} from '../../service/cart.service';
import {DataService} from '../../service/data.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  books: Book[];
  categories: Category[] = [];
  pageSize: number;
  carts: any = this.bookService.getCart();

  searchForm: FormGroup = new FormGroup({
    author: new FormControl('', [Validators.pattern('^[a-zA-Z0-9]+$')]),
    title: new FormControl('', [Validators.pattern('^[a-zA-Z0-9]+$')]),
    category: new FormControl('')
    });

  constructor(private bookService: BookService,
              private tokenStorageService: TokenStorageService,
              private cartService: CartService,
              private dataService: DataService) {
  }

  ngOnInit(): void {
    this.getAllSearch();
    this.getAllCategory();
  }

  getAllSearch() {
    this.bookService.getAllBook(0, this.searchForm.value.category, this.searchForm.value.title,
      this.searchForm.value.author).subscribe((books: any) => {
      if (books === null) {
        this.books = [];
      } else {
        this.books = books.content;
      }
    }, error => {
      this.books = null;
    });
  }

  getAllCategory(): void {
    this.bookService.getAllCategory().subscribe(category => {
      this.categories = category;
    });
  }

  searchBook() {
    this.getAllSearch();
  }

  onAddToCart(book: any) {
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
    Swal.fire({
      icon: 'success',
      title: 'Thông báo!!',
      html: 'Đã thêm vào giỏ hàng',
      showConfirmButton: false,
      timer: 1000
    }).then();
  }
}
