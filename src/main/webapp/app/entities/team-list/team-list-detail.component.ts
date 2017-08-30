import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TeamList } from './team-list.model';
import { TeamListService } from './team-list.service';

@Component({
    selector: 'jhi-team-list-detail',
    templateUrl: './team-list-detail.component.html'
})
export class TeamListDetailComponent implements OnInit, OnDestroy {

    teamList: TeamList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private teamListService: TeamListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTeamLists();
    }

    load(id) {
        this.teamListService.find(id).subscribe((teamList) => {
            this.teamList = teamList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTeamLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'teamListListModification',
            (response) => this.load(this.teamList.id)
        );
    }
}
