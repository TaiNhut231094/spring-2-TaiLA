import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../service/token-storage.service';
import {ShareService} from '../service/share.service';
import {DataService} from '../service/data.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  role: string;
  isLoggedIn = false;
  totalQuantity = 0;


  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private dataService: DataService) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
  }

  ngOnInit(): void {
    this.dataService.getData.subscribe((result: any) => {
      this.totalQuantity = parseInt(result.quantity, 10);
      });
    this.loadHeader();
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.username = this.tokenStorageService.getUser().username;
    }
    this.isLoggedIn = this.username != null;
  }

  logOut() {
    this.isLoggedIn = false;
    this.tokenStorageService.signOut();
  }
}
