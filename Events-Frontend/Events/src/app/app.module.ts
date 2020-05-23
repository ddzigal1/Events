import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialsModule} from './materials/materials.module';
import { NavigationComponent } from './components/navigation/navigation.component';
import { EventsComponent } from './components/events/events.component';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './services/user.service';
import { TokenInterceptor } from './util/http.interceptor';
import { LocalStorageService } from './util/local-storage.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { EventInfoComponent } from './components/events/components/event-info/event-info.component';
import { EventService } from './services/event.service';
import { FilterComponentComponent } from './components/events/components/filter-component/filter-component.component';
import { NotificationsService } from './services/notifications.service';
import { AddEventComponent } from './components/events/components/add-event/add-event.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterFormComponent } from './components/register/register-form/register-form.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    NavigationComponent,
    EventsComponent,
    NotificationsComponent,
    LoginComponent,
    EventInfoComponent,
    FilterComponentComponent,
    AddEventComponent,
    ProfileComponent,
    RegisterFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialsModule,
    HttpClientModule,
    
  ],
  providers: [
    UserService,
    LocalStorageService,
    NotificationsService,
    EventService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true 
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
