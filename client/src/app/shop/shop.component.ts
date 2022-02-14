import { Component, OnInit } from '@angular/core';
import { RequestsService } from '../services/requests.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  greet?: string;
  constructor(private request: RequestsService) { }

  ngOnInit(): void {
    
  }

}
