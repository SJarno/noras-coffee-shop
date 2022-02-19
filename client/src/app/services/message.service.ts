import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  successMessages: string[] = [];
  errorMessages: string[] = [];
  infoMessages: string[] = [];
  constructor() { }

  addSuccessMessage(message: string) {
    this.successMessages.push(message);
  }
  clearSuccessMessages() {
    this.successMessages = [];
  }
  addErrorMessage(message: string) {
    this.errorMessages.push(message);
  }
  clearErrorMessages() {
    this.errorMessages = [];
  }
  addInfoMessages(message: string) {
    this.infoMessages.push(message);
  }
  clearInfoMessages() {
    this.infoMessages = [];
  }
}
