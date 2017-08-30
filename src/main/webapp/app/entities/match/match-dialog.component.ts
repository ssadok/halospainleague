import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Match } from './match.model';
import { MatchPopupService } from './match-popup.service';
import { MatchService } from './match.service';
import { ResultMatch, ResultMatchService } from '../result-match';
import { TeamList, TeamListService } from '../team-list';
import { Tournament, TournamentService } from '../tournament';
import { Map, MapService } from '../map';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-match-dialog',
    templateUrl: './match-dialog.component.html'
})
export class MatchDialogComponent implements OnInit {

    match: Match;
    isSaving: boolean;

    resultmatches: ResultMatch[];

    teamlists: TeamList[];

    tournaments: Tournament[];

    maps: Map[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private matchService: MatchService,
        private resultMatchService: ResultMatchService,
        private teamListService: TeamListService,
        private tournamentService: TournamentService,
        private mapService: MapService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.resultMatchService
            .query({filter: 'match(reference)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.match.resultMatch || !this.match.resultMatch.id) {
                    this.resultmatches = res.json;
                } else {
                    this.resultMatchService
                        .find(this.match.resultMatch.id)
                        .subscribe((subRes: ResultMatch) => {
                            this.resultmatches = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.teamListService
            .query({filter: 'match(reference)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.match.teamList || !this.match.teamList.id) {
                    this.teamlists = res.json;
                } else {
                    this.teamListService
                        .find(this.match.teamList.id)
                        .subscribe((subRes: TeamList) => {
                            this.teamlists = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mapService.query()
            .subscribe((res: ResponseWrapper) => { this.maps = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.match.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchService.update(this.match));
        } else {
            this.subscribeToSaveResponse(
                this.matchService.create(this.match));
        }
    }

    private subscribeToSaveResponse(result: Observable<Match>) {
        result.subscribe((res: Match) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Match) {
        this.eventManager.broadcast({ name: 'matchListModification', content: 'OK'});
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

    trackResultMatchById(index: number, item: ResultMatch) {
        return item.id;
    }

    trackTeamListById(index: number, item: TeamList) {
        return item.id;
    }

    trackTournamentById(index: number, item: Tournament) {
        return item.id;
    }

    trackMapById(index: number, item: Map) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-match-popup',
    template: ''
})
export class MatchPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchPopupService: MatchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchPopupService
                    .open(MatchDialogComponent as Component, params['id']);
            } else {
                this.matchPopupService
                    .open(MatchDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
