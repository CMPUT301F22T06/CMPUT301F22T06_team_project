package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.git_er_done.cmput301f22t06_team_project.MainActivity.dummyMeals;
import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.MealsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.MealDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.meal.Meal;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.core.WeekDay;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;
import com.kizitonwose.calendar.view.WeekCalendarView;
import com.kizitonwose.calendar.view.WeekDayBinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

//TODO - FIX if the fragment is left, and then returned too, the selected date is no longer highlighted
/**
 * Fragment that holds the meal planner calendar and associated scrollable list of each meal planned for the selected date.
 */
public class MealPlannerFragment extends Fragment {

    CalendarView calendarView;
    private static LocalDate selectedDate = LocalDate.now();

    public static LocalDate getSelectedDate(){
        return selectedDate;
    }

    RecyclerView rvMeals;
    MealsRecyclerViewAdapter rvAdapter;

    MonthDayBinder<?> monthDayBinder;
    MonthHeaderFooterBinder<?> monthHeaderFooterBinder;

    WeekCalendarView weekCalendarView;
    WeekDayBinder weekDayBinder;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");

    Button buttonAddMealToCurrentDate;

    /**
     * Required empty public constructor
     */
    public MealPlannerFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Meal Planner");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_meal_planner, container, false);

        calendarView = root.findViewById(R.id.cv_meal_planner_calendar);
//        weekCalendarView = root.findViewById(R.id.cv_meal_planner_week_calendar);
        rvMeals = root.findViewById(R.id.rv_meal_plans);
        buttonAddMealToCurrentDate = root.findViewById(R.id.btn_meal_add_new_meal);

        //DUMMY DATA FOR TESTING BEFORE BACKEND IS SET UP
//        dummyMeals = Meal.createDummyMealList();

        setupMonthDayBinder();
        setupMonthHeaderBinder();
        setupWeekDayBinder();


        YearMonth currentMonth = YearMonth.now();
        DayOfWeek dayOfWeek = firstDayOfWeekFromLocale();
        YearMonth startDate = currentMonth.minusMonths(24);
        YearMonth endDate = currentMonth.plusMonths(24);

        calendarView.setup(startDate, endDate, dayOfWeek);
        calendarView.scrollToDate(LocalDate.now());
        calendarView.setDayBinder(monthDayBinder);
        calendarView.setMonthHeaderBinder(monthHeaderFooterBinder);

//        LocalDate startDateForWeek = LocalDate.now().minus(14, ChronoUnit.DAYS);
//        LocalDate endDateForWeek = LocalDate.now().plus(14, ChronoUnit.DAYS);
//        weekCalendarView.setup(startDateForWeek, endDateForWeek, dayOfWeek);
//        weekCalendarView.scrollToDate(LocalDate.now());
//        weekCalendarView.setDayBinder(weekDayBinder);
//        weekCalendarView.setVisibility(View.INVISIBLE);

        setupRecyclerView();

        selectedDate = LocalDate.now();
        calendarView.notifyDateChanged(selectedDate);
//        rvAdapter.updateRVToSelectedDate(selectedDate);

        buttonAddMealToCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddDialog();

                //For quick and dirty tests
//                MealDBHelper.addMealToDB(dummyMeals.get(0));
//                MealDBHelper.deleteMealFromDB(dummyMeals.get(0).getId().toString());
            }
        });

        MealDBHelper.setupSnapshotListenerForLocalMealRVAdapter(rvAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedDate = LocalDate.now();
//        rvAdapter.updateRVToSelectedDate(selectedDate);
        calendarView.notifyDateChanged(selectedDate);
    }

    private void setupRecyclerView(){
        rvAdapter = new MealsRecyclerViewAdapter();
        rvMeals.setAdapter(rvAdapter);
        rvMeals.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteIngredientCallback(rvAdapter));
//        itemTouchHelper.attachToRecyclerView(rvAdapter);
    }

    void setupMonthDayBinder(){
        monthDayBinder = new MonthDayBinder<DayViewContainer>() {
            /**
             * Binding is done in increasing chronological order from first day of month to last
             * @param container Contains the viewHolder for this binder. Call .getView() to attach view layouts accordingly
             * @param calendarDay represents the calendar day that is currently being binded as library specific CalendarDay object
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay calendarDay) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_text);
                LinearLayout dateBackground = container.getView().findViewById(R.id.layout_around_calendar_date);
                int dayInt = calendarDay.getDate().getDayOfMonth();
                text.setText(Integer.toString(dayInt));
                container.calendarDay = calendarDay;

                //if the date is within the current month
                if(container.calendarDay.getPosition() == DayPosition.MonthDate){
                    //If the date is equal to the selectedDate
                    if(container.calendarDay.getDate().equals(selectedDate)){
                        text.setTextColor(Color.WHITE);
//                        textView.setBackgroundResource(R.drawable.selection_background);
                        dateBackground.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                    else{
                        text.setTextColor(Color.WHITE);
//                        textView.setBackgroundResource(R.drawable.selection_background);
                        dateBackground.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    }
                }
            }

            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }
        };
    }


    /**
     * Binds the calendar_day_title_container layout to the calendar object.
     * Gives the calendar a header with the associated month and year.
     */
    void setupMonthHeaderBinder(){
        monthHeaderFooterBinder = new MonthHeaderFooterBinder<MonthViewContainer>() {
            @Override
            public void bind(@NonNull MonthViewContainer container, CalendarMonth calendarMonth) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_title);
                int year = calendarMonth.getYearMonth().getYear();
                String month = calendarMonth.getYearMonth().getMonth().toString();
                String calendarTitle = month + ",  " + year;
                text.setText(calendarTitle);
            }

            @NonNull
            @Override
            public MonthViewContainer create(@NonNull View view) {
                return new MonthViewContainer(view);
            }

        };
    }


    /**
     * ViewContainer for the 'all days of the month' layout of the calendarView
     * Setups on click listener for each individual day
     */
    public class DayViewContainer extends ViewContainer
    {
        TextView dateText;
        CalendarDay calendarDay;
        WeekDay calendarWeekDay;

        public DayViewContainer(@NonNull View view) {
            super(view);
            dateText = view.findViewById(R.id.tv_calendar_day_text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String dateNumString = dateText.getText().toString();
                    Log.d("Calendar", "Calendar date: " + dateNumString + "  clicked");

                    //If the date belongs to the current calendar month
                    if(calendarDay.getPosition() == DayPosition.MonthDate){
                        //if we select an already selected date
                        if(selectedDate != calendarDay.getDate()){
                            LocalDate previousDate = selectedDate;
                            selectedDate = calendarDay.getDate();
                            calendarView.notifyDateChanged(previousDate);
                            calendarView.notifyDateChanged(selectedDate);

                            //NOTIFY RECYCLERVIEW THAT THE SELECTED DATE HAS CHANGED
                            //RECYCLERVIEW ADAPTER SHOULD THEN FETCH THE MEALS ASSOCIATED WITH THIS DATE
                            rvAdapter.updateRVToSelectedDate(selectedDate);
                        }

                    }
                }
            });
        }
    }

    public class MonthViewContainer extends ViewContainer {
        public MonthViewContainer(@NonNull View view) {
            super(view);
        }
    }

    public void setupWeekDayBinder(){
        weekDayBinder = new WeekDayBinder<DayViewContainer>() {
            @Override
            public void bind(@NonNull DayViewContainer container, WeekDay weekDay) {
                container.calendarWeekDay = weekDay;
                TextView dateText = container.getView().findViewById(R.id.cv_meal_planner_week_calendar_date_text);
                TextView dayText = container.getView().findViewById(R.id.cv_meal_planner_week_calendar_day_text);

                //Set date text
                dateText.setText(dateFormatter.format(weekDay.getDate()));

                //Set day text
            }

            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }
        };
    }

    private void showAddDialog() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        MealAddEditDialogFragment editNameDialogFragment =
                MealAddEditDialogFragment.newInstance();
        editNameDialogFragment.show(fm, "fragment_meal_add_edit_dialog");
    }
}