import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Url } from '../url';
import { UrlService } from '../url.service';

@Component({
  selector: 'app-url-list',
  templateUrl: './url-list.component.html',
  styleUrls: ['./url-list.component.css']
})
export class UrlListComponent implements OnInit {

  id!: number;
  urls!: Url[];
  url!: Url;

  constructor(private urlService: UrlService, private activatedRoute: ActivatedRoute, 
    private router: Router, @Inject(DOCUMENT) private document: Document) {}

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.getUrlByUserId();
    //this.getAllUrls();
  }

  getUrlByUserId() {
    this.urlService.getUrlsByUserId(this.id).subscribe(
      data => {
        this.urls = data;
      },
      error => console.log(error)
    );
  }
  
  
  deleteUrl(urlId: number) {
    this.urlService.deleteUrl(urlId).subscribe(
      data => {
         console.log(data);
         this.getUrlByUserId();
      },
      error => console.log(error)
    );
  }

  generateUrl(){
    this.router.navigate([this.id, 'generate-url']);
  }

  redirectToOriginalUrl(urlId: number) {
    this.url = this.urls.filter(url => url.id === urlId)[0];
    this.document.location.href = this.url.originalUrl;
  }

  /*
  getAllUrls() {
    this.urlService.getUrls().subscribe(
      data => {
        this.urls = data;
        console.log('URLs are loaded');
      },
      error => console.log('could not get the URLs [url-list.ts]')
    );
  }*/

}
