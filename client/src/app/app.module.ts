import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShopComponent } from './shop/shop.component';
import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';
import { MainComponent } from './main/main.component';
import { GreetingPageComponent } from './greeting-page/greeting-page.component';

import { FormsModule } from '@angular/forms';

import {MatSelectModule} from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { XhrInterceptorService } from './services/xhr-interceptor.service';
import { MessagesComponent } from './messages/messages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardTabComponent } from './dashboard-tab/dashboard-tab.component';
import { UsersComponent } from './users/users.component';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    AppComponent,
    ShopComponent,
    NavigationComponent,
    FooterComponent,
    MainComponent,
    GreetingPageComponent,
    MessagesComponent,
    DashboardComponent,
    DashboardTabComponent,
    UsersComponent,
    SettingsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule,
    MatTabsModule,
    BrowserAnimationsModule,
    FontAwesomeModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
