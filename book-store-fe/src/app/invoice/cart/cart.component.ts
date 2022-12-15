import {Component, OnInit} from '@angular/core';
import {Book} from '../../model/book';
import {CartService} from '../../service/cart.service';
import {DataService} from '../../service/data.service';
import {render} from 'creditcardpayments/creditCardPayments';
import {TokenStorageService} from '../../service/token-storage.service';
import {Title} from '@angular/platform-browser';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  carts: any = [];
  book: Book;
  totalPrice = this.cartService.getTotalPrice();
  totalQuantity = this.cartService.getTotalQuantity();
  name: string;
  phoneNumber: string;
  street: string;
  district: string;
  provinceCity: string;


  constructor(private cartService: CartService,
              private dataService: DataService,
              private tokenStorageService: TokenStorageService,
              private title: Title) {
    this.title.setTitle('Giỏ hàng');
  }

  ngOnInit(): void {
    // tslint:disable-next-line:variable-name
    const _this = this;
    setTimeout(() => {
      _this.dataService.changeData({
        quantity: _this.cartService.getTotalQuantity()
      });
    }, 1);
    this.carts = this.cartService.getCart();
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
    if (this.tokenStorageService.getToken()) {
      this.name = this.tokenStorageService.getUser().name;
      this.street = this.tokenStorageService.getUser().address.street;
      this.district = this.tokenStorageService.getUser().address.district.name;
      this.provinceCity = this.tokenStorageService.getUser().address.district.provinceCity.name;
    }
  }

  decrease(index: number, quantity: any) {
    let decreaseQuantity = parseInt(quantity, 10) - 1;
    decreaseQuantity = decreaseQuantity > 0 ? decreaseQuantity : 1;
    this.carts[index].quantity = decreaseQuantity;
    this.cartService.saveCart(this.carts);
    this.totalPrice = this.cartService.getTotalPrice();
    this.totalQuantity = this.cartService.getTotalQuantity();
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
  }

  increase(index: number, quantity: any) {
    let increaseQuantity = parseInt(quantity, 10) + 1;
    increaseQuantity = increaseQuantity <= 999 ? increaseQuantity : 999;
    this.carts[index].quantity = increaseQuantity;
    this.cartService.saveCart(this.carts);
    this.totalPrice = this.cartService.getTotalPrice();
    this.totalQuantity = this.cartService.getTotalQuantity();
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
  }

  updateQuantity(index: number, event: any) {
    let quantity = parseInt(event.target.value, 10);
    quantity = quantity > 0 ? quantity : 1;
    quantity = quantity <= 999 ? quantity : 999;
    event.target.value = quantity;
    this.carts[index].quantity = quantity;
    this.cartService.saveCart(this.carts);
    this.totalQuantity = this.cartService.getTotalQuantity();
    this.totalPrice = this.cartService.getTotalPrice();
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
  }

  deleteCart(index: number) {
    // tslint:disable-next-line:variable-name
    const _this = this;
    Swal.fire({
      icon: 'success',
      title: 'Thông báo!!',
      html: 'Đã xóa sản phẩm khỏi giỏ hàng',
      showConfirmButton: false,
      timer: 1000
    }).then();
    _this.carts.splice(index, 1);
    _this.cartService.saveCart(_this.carts);
    this.totalPrice = this.cartService.getTotalPrice();
    this.totalQuantity = this.cartService.getTotalQuantity();
    this.dataService.changeData({
      quantity: this.cartService.getTotalQuantity()
    });
  }

  payment() {
    // document.getElementById('myPayPalButtons').innerHTML = '<div id="btnPaypal"></div>';
    const username = this.tokenStorageService.getUser().username;
    render({
      id: '#myPayPalButtons',
      currency: 'VND',
      value: String((this.totalPrice / 23000).toFixed(2)),
      onApprove: () => {
        for (const item of this.carts) {
          item.book = {
            id: item.id
          };
        }
        Swal.fire('Thông báo!!', 'Thanh toán thành công. </br>Sách của bạn sẽ được giao trong vòng 3 ngày tới', 'success').then();
        this.carts = [];
        this.cartService.saveCart(this.carts);
        this.dataService.changeData({
          quantity: this.cartService.getTotalQuantity()
        });
      }
    });
  }
}
