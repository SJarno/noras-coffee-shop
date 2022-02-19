import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { faCoffee } from '@fortawesome/free-solid-svg-icons';
import { faBars } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  credentials = { 'username': '', 'password': '' };
  logoText: string;
  showNavBar: boolean;
  showLoginForm: boolean;
  faCoffee = faCoffee;
  faBars = faBars;
  /* authenticated: boolean = false; */

  @ViewChild('testi')
  navItems?: ElementRef;

  constructor(public auth: AuthService, private route: Router) {
    this.logoText = "Noras Coffee Shop";
    this.showNavBar = true;
    this.showLoginForm = false;

  }

  ngOnInit(): void {


  }

  toggleNavBar() {
    this.showNavBar = !this.showNavBar;
    if (this.showNavBar) {
      this.navItems?.nativeElement.setAttribute("style", "display:none;");
    } else {
      this.navItems?.nativeElement.setAttribute("style", "display:flex;");

    }

  }
  toggleLoginForm() {
    this.showLoginForm = !this.showLoginForm;
  }
  
  login() {
    this.auth.login(this.credentials, () => {
      
      this.route.navigateByUrl("/");
    });
    return false;
  }
  logout() {
    this.auth.logout();
    this.credentials = { 'username': '', 'password': '' };
  }

}
