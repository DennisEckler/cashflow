import { Component } from '@angular/core';

@Component({
  selector: 'myTest',
  template: ` <div style="text-align:center" class="container">
    <h1>Welcome {{ title }}!</h1>
    <div class="container">
      <p>
        Id: <span>{{ greeting.id }}</span>
      </p>
      <p>
        Message: <span>{{ greeting.content }}!</span>
      </p>
    </div>
  </div>`,
  styles: ':host{display: grid; justify-content: center;}',
  standalone: true,
})
export class TestComponent {
  title = 'Demo';
  greeting = { id: 'XXX', content: 'Hello World' };
}
