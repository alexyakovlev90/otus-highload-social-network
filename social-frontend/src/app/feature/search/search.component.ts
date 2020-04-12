import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor() { }

  users = [
    { name: 'Name', login: 'login'},
    { name: 'Name', login: 'login'}
  ];

  ngOnInit(): void {
  }

}
