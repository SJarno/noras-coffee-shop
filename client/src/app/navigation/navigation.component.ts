import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { faCoffee } from '@fortawesome/free-solid-svg-icons';
import { faBars } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  logoText: string;
  showNavBar: boolean;
  faCoffee = faCoffee;
  faBars = faBars;

  @ViewChild('testi') 
  navItems?: ElementRef;

  constructor() { 
    this.logoText = "Noras Coffee Shop";
    this.showNavBar = true;
    
  }

  ngOnInit(): void {
  }
  toggleNavBar() {
    this.showNavBar = !this.showNavBar;
    if (this.showNavBar) {
      this.navItems?.nativeElement.setAttribute("style", "display:none;");
    }  else {
      this.navItems?.nativeElement.setAttribute("style", "display:flex;");
      
    }
    //this.showNavBar? console.log(this.navItems?.nativeElement): console.log("kiinni");
    
    
  }

}
