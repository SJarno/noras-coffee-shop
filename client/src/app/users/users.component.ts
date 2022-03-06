import { Component, OnInit } from '@angular/core';
import { UserDto } from '../modules/user-dto';
import { EmployeeServiceService } from '../services/employee-service.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  employee?: UserDto;
  employees?: UserDto[];
  newEmployee!: UserDto;

  constructor(private employeeService: EmployeeServiceService) {
    this.newEmployee = new UserDto();
   }

  ngOnInit(): void {
  }
  addNewEmployee() {
    console.log("yritetään tallentaa")
    console.log(this.newEmployee);
    this.employeeService.createNewEmployee(this.newEmployee).subscribe(result => {
      console.log(result);
    });
  }

}
