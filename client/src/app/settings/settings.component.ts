import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { RequestsService } from '../services/requests.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  displayModify: boolean = false;
  newUsername?: string;
  oldUsername?: string;

  constructor(public authService: AuthService, private request: RequestsService) {
    
   }

  ngOnInit(): void {
    this.newUsername = this.authService.username;
    this.oldUsername = this.authService.username;
  }
  toggleModify(): void {
    this.displayModify = !this.displayModify;
  }
  updateUsername(): any {
    console.log(this.newUsername);
    console.log(this.authService.username);
    
    this.request.updateUsername(this.oldUsername, this.newUsername).subscribe(result => {
      console.log("Tulos:")
      console.log(result);
      console.log(result.username);
    });
  }

}
