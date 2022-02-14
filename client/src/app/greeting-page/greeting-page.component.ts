import { Component, OnInit } from '@angular/core';
import { RequestsService } from '../services/requests.service';

@Component({
  selector: 'app-greeting-page',
  templateUrl: './greeting-page.component.html',
  styleUrls: ['./greeting-page.component.css']
})
export class GreetingPageComponent implements OnInit {
  greet?:string;
  constructor(private request: RequestsService) { }

  ngOnInit(): void {
    this.request.getGreeting().subscribe(response => {
      /* this.greet = response.body.greet; */
      console.log(response.body);
      console.log(response.body.greet);
      this.greet = response.body.greet;
    });
  }

}
