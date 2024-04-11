import {Component, EventEmitter, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-search-box',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './search-box.component.html',
  styleUrl: './search-box.component.css'
})
export class SearchBoxComponent {
  @Output() searchTerm = new EventEmitter<string>();

  search(searchTerm: string): void {
    this.searchTerm.emit(searchTerm);
  }
}
