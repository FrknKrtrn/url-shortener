import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Url } from '../url';
import { UrlDto } from '../url-dto';
import { UrlService } from '../url.service';

@Component({
  selector: 'app-generate-url',
  templateUrl: './generate-url.component.html',
  styleUrls: ['./generate-url.component.css']
})
export class GenerateUrlComponent implements OnInit {

  id!: number;
  url = new Url();
  urlDto = new UrlDto();
  isGenerated = false;
  @ViewChild('shortenerForm', { static: false })
  shortenerForm!: NgForm;

  constructor(private urlService: UrlService, private router: Router , private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
  }

  generateUrl() {
    this.urlService.generateUrl(this.urlDto, this.id).subscribe(
      data => {
        this.url = data;
        this.isGenerated = true;
        console.log('Shortened URL is generated');
      },
      error => console.log('Shortened URL could not be generated [generate-url]')
    );
  }

  clear(){
    this.shortenerForm.resetForm();
    this.isGenerated = false;
  }
  onSubmit() {
    this.generateUrl();
  }

  goToUrlList(){
    this.router.navigate([this.id, 'url-list']);
  }

}
