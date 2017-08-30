/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TeamListDetailComponent } from '../../../../../../main/webapp/app/entities/team-list/team-list-detail.component';
import { TeamListService } from '../../../../../../main/webapp/app/entities/team-list/team-list.service';
import { TeamList } from '../../../../../../main/webapp/app/entities/team-list/team-list.model';

describe('Component Tests', () => {

    describe('TeamList Management Detail Component', () => {
        let comp: TeamListDetailComponent;
        let fixture: ComponentFixture<TeamListDetailComponent>;
        let service: TeamListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [TeamListDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TeamListService,
                    JhiEventManager
                ]
            }).overrideTemplate(TeamListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeamListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TeamList(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.teamList).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
