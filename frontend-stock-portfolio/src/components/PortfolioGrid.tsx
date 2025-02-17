import React from 'react';
import {Portfolio} from "../services/api";
import { Card, CardContent, Typography, Paper, Grid2 } from '@mui/material';

interface PortfolioGridProps {
    portfolio: Portfolio;
}

const PortfolioGrid: React.FC<PortfolioGridProps> = ({ portfolio }) => {
    return (
        <Grid2 container spacing={3} sx={{ mt: 2, mb: 2 }}>
            {portfolio.holdings.map((holding, index) => (
                <Grid2
                    key={index}
                    sx={{ xs: 12, sm: 6, md: 4 }}>
                    <Paper elevation={3} sx={{ padding: '1rem' }}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6" component="h2">
                                    {holding.stock.name} ({holding.stock.symbol})
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    Quantity Owned: {holding.sharesOwned}
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    Purchase Price: ${holding.purchasePrice.toFixed(2)}
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    Current Price: ${holding.stock.currentPrice.toFixed(2)}
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    Total Value: ${(holding.sharesOwned * holding.stock.currentPrice).toFixed(2)}
                                </Typography>
                                <Typography variant="body1" color="textSecondary">
                                    Total Profit : {(((holding.sharesOwned * holding.stock.currentPrice - holding.sharesOwned * holding.purchasePrice) / (holding.sharesOwned * holding.purchasePrice)) * 100).toFixed(2)}%
                                </Typography>
                            </CardContent>
                        </Card>
                    </Paper>
                </Grid2>
            ))}
        </Grid2>
    );
};

export default PortfolioGrid;
