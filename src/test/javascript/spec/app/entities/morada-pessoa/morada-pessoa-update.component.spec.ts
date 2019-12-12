import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { MoradaPessoaUpdateComponent } from 'app/entities/morada-pessoa/morada-pessoa-update.component';
import { MoradaPessoaService } from 'app/entities/morada-pessoa/morada-pessoa.service';
import { MoradaPessoa } from 'app/shared/model/morada-pessoa.model';

describe('Component Tests', () => {
  describe('MoradaPessoa Management Update Component', () => {
    let comp: MoradaPessoaUpdateComponent;
    let fixture: ComponentFixture<MoradaPessoaUpdateComponent>;
    let service: MoradaPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MoradaPessoaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MoradaPessoaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MoradaPessoaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MoradaPessoaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MoradaPessoa(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MoradaPessoa();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
