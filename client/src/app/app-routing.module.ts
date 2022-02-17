import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GreetingPageComponent } from './greeting-page/greeting-page.component';
import { ShopComponent } from './shop/shop.component';

const routes: Routes = [
  { path: '', component: GreetingPageComponent },
  { path: 'shop', component: ShopComponent },
  //{ path: '', redirectTo: 'greet-page' },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
