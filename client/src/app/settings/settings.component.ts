import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
 
  newPassword?: any;
  oldPassword?: string;
  retypedPassword?: string;

  constructor(public authService: AuthService,
    private request: RequestsService, private route: Router) {

  }

  ngOnInit(): void {
    this.newUsername = this.authService.username;
    this.oldUsername = this.authService.username;
  }
  toggleModify(): void {
    this.displayModify = !this.displayModify;
  }
  updateUsername(): any {
    console.log("Kun vaihdetaan käyttäjätunnus")
    console.log(this.newUsername);
    console.log(this.authService.username);
    console.log("Credentials before update: ");
    console.log(this.authService.credentials);

    this.request.updateUsername(this.newUsername).subscribe(result => {
      //Päivitetään käyttäjänimi
      console.log("Tulostetaan vastaus: ");
      console.log(result);
      console.log("Tulostetaan body:")
      console.log(result.body);
      if (result.status === 200) {
        this.authService.username = result.body.username;
        this.authService.credentials.username = result.body.username;
      }

      console.log("Credentials after update: ");
      console.log(this.authService.credentials);
      this.authService.updateUsername(this.authService.credentials, () => {
        this.route.navigateByUrl("/dashboard");
      })
    });

  }
  updatePassword() {
    this.request.updatePassword(this.newPassword, this.oldPassword).subscribe(result => {
      console.log("Tulos");
      console.log(result);
      if (result.status === 200) {
        console.log("Päivitetty juu");
        this.authService.credentials.password = this.newPassword;
      }
      this.authService.updateUsername(this.authService.credentials, () => {
        this.route.navigateByUrl("/dashboard");
      })
    });
  }
  private passwordsEquals() {
    return this.oldPassword === this.authService.credentials.password && this.newPassword === this.retypedPassword;
  }

}
