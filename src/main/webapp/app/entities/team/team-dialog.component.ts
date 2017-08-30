import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamPopupService } from './team-popup.service';
import { TeamService } from './team.service';
import { Player, PlayerService } from '../player';
import { TeamList, TeamListService } from '../team-list';
import { Division, DivisionService } from '../division';
import { Tournament, TournamentService } from '../tournament';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-dialog',
    templateUrl: './team-dialog.component.html'
})
export class TeamDialogComponent implements OnInit {

    team: Team;
    isSaving: boolean;

    players: Player[];

    teamlists: TeamList[];

    divisions: Division[];

    tournaments: Tournament[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private teamService: TeamService,
        private playerService: PlayerService,
        private teamListService: TeamListService,
        private divisionService: DivisionService,
        private tournamentService: TournamentService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.playerService.query()
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.teamListService.query()
            .subscribe((res: ResponseWrapper) => { this.teamlists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.team, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teamService.update(this.team));
        } else {
            this.subscribeToSaveResponse(
                this.teamService.create(this.team));
        }
    }

    private subscribeToSaveResponse(result: Observable<Team>) {
        result.subscribe((res: Team) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Team) {
        this.eventManager.broadcast({ name: 'teamListModification', content: 'OK'});
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

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }

    trackTeamListById(index: number, item: TeamList) {
        return item.id;
    }

    trackDivisionById(index: number, item: Division) {
        return item.id;
    }

    trackTournamentById(index: number, item: Tournament) {
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
    selector: 'jhi-team-popup',
    template: ''
})
export class TeamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamPopupService: TeamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.teamPopupService
                    .open(TeamDialogComponent as Component, params['id']);
            } else {
                this.teamPopupService
                    .open(TeamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
