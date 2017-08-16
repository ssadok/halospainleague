import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SocialAccount } from './social-account.model';
import { SocialAccountService } from './social-account.service';

@Component({
    selector: 'jhi-social-account-detail',
    templateUrl: './social-account-detail.component.html'
})
export class SocialAccountDetailComponent implements OnInit, OnDestroy {

    socialAccount: SocialAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private socialAccountService: SocialAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSocialAccounts();
    }

    load(id) {
        this.socialAccountService.find(id).subscribe((socialAccount) => {
            this.socialAccount = socialAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSocialAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'socialAccountListModification',
            (response) => this.load(this.socialAccount.id)
        );
    }
}
