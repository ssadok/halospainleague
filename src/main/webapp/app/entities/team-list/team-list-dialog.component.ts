import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TeamList } from './team-list.model';
import { TeamListPopupService } from './team-list-popup.service';
import { TeamListService } from './team-list.service';
import { Team, TeamService } from '../team';
import { Match, MatchService } from '../match';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-list-dialog',
    templateUrl: './team-list-dialog.component.html'
})
export class TeamListDialogComponent implements OnInit {

    teamList: TeamList;
    isSaving: boolean;

    teams: Team[];

    matches: Match[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private teamListService: TeamListService,
        private teamService: TeamService,
        private matchService: MatchService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.teamService.query()
            .subscribe((res: ResponseWrapper) => { this.teams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.teamList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teamListService.update(this.teamList));
        } else {
            this.subscribeToSaveResponse(
                this.teamListService.create(this.teamList));
        }
    }

    private subscribeToSaveResponse(result: Observable<TeamList>) {
        result.subscribe((res: TeamList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TeamList) {
        this.eventManager.broadcast({ name: 'teamListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackTeamById(index: number, item: Team) {
        return item.id;
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-team-list-popup',
    template: ''
})
export class TeamListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamListPopupService: TeamListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.teamListPopupService
                    .open(TeamListDialogComponent as Component, params['id']);
            } else {
                this.teamListPopupService
                    .open(TeamListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
