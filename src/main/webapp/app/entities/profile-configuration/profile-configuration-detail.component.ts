import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileConfiguration } from './profile-configuration.model';
import { ProfileConfigurationService } from './profile-configuration.service';

@Component({
    selector: 'jhi-profile-configuration-detail',
    templateUrl: './profile-configuration-detail.component.html'
})
export class ProfileConfigurationDetailComponent implements OnInit, OnDestroy {

    profileConfiguration: ProfileConfiguration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profileConfigurationService: ProfileConfigurationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfileConfigurations();
    }

    load(id) {
        this.profileConfigurationService.find(id).subscribe((profileConfiguration) => {
            this.profileConfiguration = profileConfiguration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfileConfigurations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profileConfigurationListModification',
            (response) => this.load(this.profileConfiguration.id)
        );
    }
}
